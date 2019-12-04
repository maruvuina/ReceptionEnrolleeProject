package by.epam.receptionenrollee.service;

import by.epam.receptionenrollee.dao.impl.UserDaoImpl;
import by.epam.receptionenrollee.dao.pool.EntityTransaction;
import by.epam.receptionenrollee.entity.RoleEnum;
import by.epam.receptionenrollee.entity.User;
import by.epam.receptionenrollee.exception.DaoException;
import by.epam.receptionenrollee.factory.DaoFactory;
import by.epam.receptionenrollee.logic.SessionRequestContent;
import by.epam.receptionenrollee.validator.HashUtil;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import static by.epam.receptionenrollee.command.RequestParam.*;

public class UserService {
    private static final Logger logger = Logger.getLogger(UserService.class);

    public boolean verifyUserEmail(String email) {
        boolean isVerified;
        UserDaoImpl userDaoImpl = DaoFactory.getInstance().getUserDao();
        EntityTransaction transaction = new EntityTransaction();
        transaction.begin(userDaoImpl);
        try {
            isVerified = userDaoImpl.isUserEmailUnique(email);
            transaction.commit();
        } finally {
            transaction.end();
        }
        return isVerified;
    }

//    public RoleEnum getUserRole(String login, String password) {
//        RoleEnum roleEnum;
//        UserDaoImpl userDaoImpl = DaoFactory.getInstance().getUserDao();
//        EntityTransaction transaction = new EntityTransaction();
//        transaction.begin(userDaoImpl);
//        try {
//            if (userDaoImpl.getRoleByLoginPassword(login, password).equals(RoleEnum.ADMIN)) {
//                roleEnum = RoleEnum.ADMIN;
//            } else if (userDaoImpl.getRoleByLoginPassword(login, password).equals(RoleEnum.USER)) {
//                roleEnum =  RoleEnum.USER;
//            } else {
//                roleEnum = RoleEnum.UNKNOWN;
//            }
//            transaction.commit();
//        }
//        finally {
//            transaction.end();
//        }
//        return roleEnum;
//    }

    public User getUserByEmailPassword(String email, String password) {
        User user = null;
        UserDaoImpl userDaoImpl = DaoFactory.getInstance().getUserDao();
        EntityTransaction transaction = new EntityTransaction();
        transaction.begin(userDaoImpl);
        try {
            user = userDaoImpl.findUserByLoginPassword(email, password);
            transaction.commit();
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Error while trying to get user: ", e);
        } finally {
            transaction.end();
        }
        return user;
    }

    public User registerUser(SessionRequestContent sessionRequestContent) {
        User newUser = new User();
        UserDaoImpl userDaoImpl = DaoFactory.getInstance().getUserDao();
        EntityTransaction transaction = new EntityTransaction();
        transaction.begin(userDaoImpl);
        try {
            newUser.setFirstName(sessionRequestContent.getParameter(PARAM_NAME_FULLNAME));
            newUser.setLastName(sessionRequestContent.getParameter(PARAM_NAME_SURNAME));
            newUser.setMiddleName(sessionRequestContent.getParameter(PARAM_NAME_MIDDLENAME));
            newUser.setEmail(sessionRequestContent.getParameter(PARAM_NAME_LOGIN));
            String passwordHash = HashUtil.hashString(sessionRequestContent.getParameter(PARAM_NAME_PASSWORD));
            newUser.setPassword(passwordHash);
            newUser.setRoleEnum(RoleEnum.USER);
            userDaoImpl.insertUser(newUser);
            transaction.commit();
        } catch (DaoException e) {
            transaction.rollback();
            logger.log(Level.ERROR, "Error while register: ", e);
        } finally {
            transaction.end();
        }
        return newUser;
    }

    public User getUserById(int idUser) {
        User user = null;
        UserDaoImpl userDaoImpl = DaoFactory.getInstance().getUserDao();
        EntityTransaction transaction = new EntityTransaction();
        transaction.begin(userDaoImpl);
        try {
            user = userDaoImpl.findUserById(idUser);
            System.out.println("getUserById--> " + user.toString());
            transaction.commit();
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Error while trying to get user by id: ", e);
        } finally {
            transaction.end();
        }
        return user;
    }

}
