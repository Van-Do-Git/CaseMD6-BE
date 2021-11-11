package com.codegym.casemd6.model;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Song{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String path;
    private String singer;
    private Long count;
    private Long countLike;
    @ManyToOne
    @LazyCollection(LazyCollectionOption.FALSE)
    private Account account;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "song", orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonManagedReference
    private List<AccountLike> likeList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "song", orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonManagedReference
    private List<Comment> commentList;

    @ManyToMany(fetch = FetchType.LAZY, targetEntity = Playlist.class)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Playlist> playlists;
}
