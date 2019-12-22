package ru.byprogminer.Lab4_Web.api.v1.users;

public class UserDto {

    public final long id;
    public final String username;

    public UserDto(long id, String username) {
        this.id = id;
        this.username = username;
    }
}
