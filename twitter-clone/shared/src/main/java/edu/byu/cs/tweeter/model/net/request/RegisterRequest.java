package edu.byu.cs.tweeter.model.net.request;

public class RegisterRequest {

        private String firstName;
        private String lastName;
        private String username; // alias
        private String password;
        private String imageURL;

    /**
         * Allows construction of the object from Json. Private so it won't be called in normal code.
         */
        private RegisterRequest() {}

        /**
         * Creates an instance.
         *
         * @param username the username of the user to be registerd.
         * @param password the password of the user to be registerd.
         */
        public RegisterRequest(String firstName, String lastName, String username, String password, String imageUrl) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.username = username;
            this.password = password;
            this.imageURL = imageUrl;

        }

        /**
         * Returns the username of the user to be registerd by this request.
         *
         * @return the username.
         */
        public String getUsername() {
            return username;
        }

        /**
         * Sets the username.
         *
         * @param username the username.
         */
        public void setUsername(String username) {
            this.username = username;
        }
        /**
         * Returns the password of the user to be registerd by this request.
         *
         * @return the password.
         */
        public String getPassword() {
            return password;
        }

        /**
         * Sets the password.
         *
         * @param password the password.
         */
        public void setPassword(String password) {
            this.password = password;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getImageURL() {
            return imageURL;
        }

        public void setImageURL(String imageURL) {
            this.imageURL = imageURL;
        }

}
