package com.cookos.server;

import java.io.*;
import java.net.*;
import java.util.Arrays;

import org.apache.logging.log4j.*;

import com.cookos.dao.GenericDao;
import com.cookos.model.*;
import com.cookos.net.*;

public class LoginServerTask implements Runnable {
    private static final Logger logger = LogManager.getLogger(LoginServerTask.class);

    private Socket socket;
    private ObjectOutputStream ostream;
    private ObjectInputStream istream;

    public LoginServerTask(Socket socket) throws IOException
    {
        this.socket = socket;
        ostream = new ObjectOutputStream(socket.getOutputStream());
        ostream.flush();
        istream = new ObjectInputStream(socket.getInputStream());
    }

    @Override
    public void run() {
        
        try {
            handleLogin().run();
        } catch (Exception e) {
            logger.info("%s:%d disconnected".formatted(socket.getInetAddress(), socket.getPort()));
        }
        
    }

    private Runnable handleLogin() throws IOException, ClassNotFoundException, Exception {
        while (true) {
            var login = (String)istream.readObject();
            
            int hashLength = istream.readInt();
            var password = new byte[hashLength];
            istream.readFully(password);

            try (var userDao = new GenericDao<>(User.class)) {

                var user = userDao.findByUniqueColumn("login", login);

                if (user == null) {
                    ostream.writeObject(LoginMessage.WrongLogin);
                    ostream.flush();
                    
                    continue;
                }

                if (Arrays.equals(password, user.getPassword())) {
                   ostream.writeObject(LoginMessage.WrongPassword);
                    ostream.flush();

                    continue;
                }

                ostream.writeObject(LoginMessage.Success);
                ostream.flush();
                ostream.writeObject(user.getRole());
                ostream.flush();

                if (user.getRole() == UserRole.Admin) {
                    return new AdminServerTask(socket, ostream, istream);
                } else {
                    return new StudentServerTask(socket, ostream, istream, user.getId(), Integer.valueOf(user.getLogin()));
                }
            }
        }
    }
}
