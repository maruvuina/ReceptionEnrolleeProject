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

    GOTOREGISTER {
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

    BACK_TO_ADMIN_HOME_COMMAND {
        {
            this.command = new BackToAdminHomeCommand();
        }
    };

    ActionCommand command;

    public ActionCommand getCommand() {
        return command;
    }
}
