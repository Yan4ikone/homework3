package data;


public class RegistrationDto {

    private String email;
    private String password;

    public RegistrationDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public RegistrationDto() {
        super();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}


