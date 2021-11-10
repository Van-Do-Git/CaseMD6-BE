package com.codegym.casemd6.model;



import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;

@Data
@Entity
public class AccountLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_id")
    @LazyCollection(LazyCollectionOption.FALSE)
    private Account account;

    @ManyToOne
    @JoinColumn(name = "song_id")
    @JsonBackReference
    private Song song;

    @ManyToOne
    @JoinColumn(name = "playlist_id")
    private Playlist playlist;
}
