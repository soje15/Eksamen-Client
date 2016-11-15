package sdk.models;

import java.util.Date;

//Model på klienten - Model på server siden, kan implementeres på klient-siden.
//På alle de andre modeller, har vi fælles variabler, eks. ID.
//Derfor er det en god idé at gemme dem i en abstrakt klasse, som de alle sammen arver fra.
//Abstract vil sige, at man ikke kan lave en instans af klassen. Det er en måde, man siger
//Til implementationen, at det ikke er meningen man skal lave en instans af klassen. Dens formål er nedarving.

public abstract class BaseModel {

    private String id;
    private Date createdAt;
    private Date updatedAt;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
