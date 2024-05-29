package org.vadim.client;

import java.util.Objects;

public class Client {
    private String name;
    private int id;
    private String password;
    private String ip;
    private static int lastID = 1000;

    public Client() {
        this.name = "Jon Doe";
        this.password = "password";
        this.ip = "127.0.0.1";
        this.id = ++lastID;


    }

    public Client(String name, String password, String ip) {
        this.name = name;
        this.password = password;
        this.ip = ip;
        this.id = ++lastID;
    }

    public Client(String name, String password, String ip, int id) {
        this.name = name;
        this.password = password;
        this.ip = ip;
        this.id = id;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return id == client.id && Objects.equals(name, client.name) && Objects.equals(password, client.password) && Objects.equals(ip, client.ip);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id, password, ip);
    }
}
