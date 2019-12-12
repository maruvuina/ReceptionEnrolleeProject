package by.epam.receptionenrollee.command;

import by.epam.receptionenrollee.command.impl.*;

public enum CommandType {
    LOGIN {
        {
            this.command =
                    new LoginCommand();
        }
    },

    LOGOUT {
        {
            this.command = new LogoutCommand();
        }
    },

    REGISTER {
        {
            this.command =
                    new RegisterCommand();
        }
    },

    GO_TO_REGISTER {
        {
            this.command = new GoToRegisterCommand();
        }
    },

    COMPITITION {
        {
            this.command = new ComptitionRegisterCommand();
        }
    },

    NOTIFICATION {
        {
            this.command = new NotificationCommand();
        }
    },

    ENROLLEES_BY_FACULTY {
        {
            this.command = new EnrolleesByFacultyCommand();
        }
    },

    BACK_TO_ADMIN_HOME {
        {
            this.command = new BackToAdminHomeCommand();
        }
    },

    CONTACT {
        {
            this.command = new ContactCommand();
        }
    };

    ActionCommand command;

    public ActionCommand getCommand() {
        return command;
    }
}
