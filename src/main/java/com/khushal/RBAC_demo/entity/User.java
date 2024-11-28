package com.khushal.RBAC_demo.entity;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Document(collection = "users")
public class User {

    @Id
    private ObjectId id;

    // Indexing will not create automatically unless we mention it in .properties file
    @Indexed(unique = true)
    @NonNull
    private String userName;

    private String email;

    private boolean isActive;

    @NonNull
    private String password;

    @DBRef
    private List<JournalEntry> entries = new ArrayList<>();

    private List<String> roles;
}
