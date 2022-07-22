package mx.com.server.cae.models;

import java.util.Date;
import org.springframework.data.mongodb.core.mapping.Field;

public class Role {

    @Field("name")
    private ERole name;

    @Field("description")
    private String description;

    @Field("created_date")
    private Date createdDate;

    @Field("last_changed_date")
    private Date lastChangedDate;

    public Role() {
    }

    public ERole getName() {
        return name;
    }

    public void setName(ERole name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastChangedDate() {
        return lastChangedDate;
    }

    public void setLastChangedDate(Date lastChangedDate) {
        this.lastChangedDate = lastChangedDate;
    }

    @Override
    public String toString() {
        return "Role{" + "name=" + name + ", description=" + description + ", createdDate=" + createdDate + ", lastChangedDate=" + lastChangedDate + '}';
    }
}
