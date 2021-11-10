package com.codegym.casemd6.model;



import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;


@Data
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    @ManyToOne
    private Account account;

    @ManyToOne
    @JoinColumn(name = "song_id")
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonBackReference
    private Song song;

    @ManyToOne
    @JoinColumn(name = "playlist_id")
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonBackReference
    private Playlist playlist;
}

