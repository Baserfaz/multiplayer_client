package pojos;

public enum Command {
    LOGIN {
        @Override
        public String query(String db) {
            return "UPDATE " + db + ".users " +
                    "SET lastLoginDate = ? " +
                    "WHERE username = ?";
        }
    },
    LOGOUT {
        @Override
        public String query(String db) {
            return null;
        }
    },
    CHANGE_PASSWORD {
        @Override
        public String query(String db) {
            return "UPDATE " + db + ".users " +
                    "SET password = ? " +
                    "WHERE username = ?";
        }
    },
    CREATE_USER {
        @Override
        public String query(String db) {
            return "INSERT INTO " + db + ".users " +
                    "(username, password, salt) " +
                    "VALUES (?,?,?)";
        }
    },
    REMOVE_USER {
        @Override
        public String query(String db) {
            return "DELETE FROM " + db + ".users " +
                    "WHERE username = ?";
        }
    },
    GET_USER_ID {
        @Override
        public String query(String db) {
            return "SELECT id FROM " + db + ".users WHERE username = ?";
        }
    }
    ;

    public abstract String query(String db);
}
