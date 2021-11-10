package com.codegym.casemd6.controller;

import com.codegym.casemd6.dto.*;
import com.codegym.casemd6.model.*;
import com.codegym.casemd6.service.account.IServiceAccount;
import com.codegym.casemd6.service.accountlike.IServiceLike;
import com.codegym.casemd6.service.comment.IServiceComment;
import com.codegym.casemd6.service.image.IServiceImage;
import com.codegym.casemd6.service.playlist.IServicePlaylist;
import com.codegym.casemd6.service.song.ISongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.relational.core.sql.Like;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/user")
@EnableSpringDataWebSupport
public class MusicController {
    @Autowired
    ISongService songService;
    @Autowired
    IServiceAccount serviceAccount;

    @Autowired
    IServiceComment serviceComment;
    @Autowired
    IServiceLike serviceLike;
    @Autowired
    IServiceImage serviceImage;
    @Autowired
    IServicePlaylist servicePlaylist;

    @PostMapping("/create")
    public ResponseEntity<MesageRespons> createSong(@RequestBody SongInit song) {
        Account account = serviceAccount.findById(song.getIdAccount()).get();
        Song newSong = new Song();
        newSong.setName(song.getName());
        newSong.setPath(song.getPath());
        newSong.setSinger(song.getSinger());
        newSong.setAccount(account);
        newSong.setCount(0L);
        newSong.setCountLike(0L);
        songService.save(newSong);
        MesageRespons mesageRespons = new MesageRespons();
        mesageRespons.setMesage("ok");
        return new ResponseEntity<>(mesageRespons, HttpStatus.OK);
    }

    @GetMapping("/songs/{id}")
    public ResponseEntity<List<Song>> findAllSongByAccount(@PathVariable("id") Long idAccount) {
        List<Song> songList = new ArrayList<>();
        songList = (List<Song>) songService.findByAcount(idAccount);
        return new ResponseEntity<>(songList, HttpStatus.OK);
    }

    @PostMapping("/comment")
    public ResponseEntity<MesageRespons> createCommentForSong(@RequestBody CommentDto comment) {
        Comment newComment = new Comment();
        Account account = serviceAccount.findById(comment.getIdAccount()).get();
        Song song = songService.findById(comment.getIdSong()).get();
        String text = comment.getText();
        newComment.setAccount(account);
        newComment.setSong(song);
        newComment.setText(text);
        serviceComment.save(newComment);
        MesageRespons mesageRespons = new MesageRespons();
        mesageRespons.setMesage("ok");
        return new ResponseEntity<>(mesageRespons, HttpStatus.OK);
    }

    @GetMapping("/song/{id}")
    public ResponseEntity<Song> findSong(@PathVariable("id") Long idSong) {
        Song song = songService.findById(idSong).get();
        return new ResponseEntity<>(song, HttpStatus.OK);
    }

    @PostMapping("/like")
    public ResponseEntity<?> like(@RequestBody LikeDto likeDto) {
        Account account = serviceAccount.findById(likeDto.getIdAccount()).get();
        Song song = songService.findById(likeDto.getIdSong()).get();
        song.setCountLike(song.getCountLike() + 1);
        AccountLike like = new AccountLike();
        like.setAccount(account);
        like.setSong(song);
        serviceLike.save(like);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/like/{idLike}")
    public ResponseEntity<?> unlike(@PathVariable("idLike") Long idLike) {
        AccountLike like = serviceLike.findById(idLike).get();
        Song song = like.getSong();
        song.setCountLike(song.getCountLike() - 1);
        songService.save(song);
        serviceLike.remove(idLike);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteSong(@PathVariable("id") Long id) {
        Song song = songService.findById(id).get();
        if (song == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        songService.remove(id);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @PostMapping("/creatlist")
    public ResponseEntity<?> creatList(@RequestBody PlaylistDto playlistDto) {
        Account account = serviceAccount.findById(playlistDto.getIdAccount()).get();
        Date date = new Date();
        Image image = new Image();
        image.setPath(playlistDto.getPath());
        image = serviceImage.add(image);
        Playlist playlist = new Playlist();
        playlist.setName(playlistDto.getName());
        playlist.setAccount(account);
        playlist.setImage(image);
        playlist.setDate(date);
        servicePlaylist.save(playlist);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/playlists/{id}")
    public ResponseEntity<List<Playlist>> findAllByID(@PathVariable("id") Long id) {
        List<Playlist> playlist = servicePlaylist.findAllByAccount_Id(id);
        return new ResponseEntity<>(playlist, HttpStatus.OK);
    }

    @GetMapping("/playlist/{id}")
    public ResponseEntity<List<Song>> findAllSongOfPlaylist(@PathVariable("id") Long id) {
        Playlist playlist = servicePlaylist.findById(id).get();
        List<Song> songList = (List<Song>) playlist.getSongs();
        return new ResponseEntity<>(songList, HttpStatus.OK);
    }

    @GetMapping("/findSongByName/{name}")
    public ResponseEntity<List<Song>> findAllSongByName(@PathVariable("name") String name) {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id").descending());
        Page<Song> songPage = songService.findSongsByNameContaining(pageable, name);
        List<Song> list = songPage.getContent();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("/addSongToList")
    public ResponseEntity<?> addSongToList(@RequestBody PlaylistAddDto playlistAddDto) {
        Long idPlaylist = playlistAddDto.getIdPlaylist();
        Playlist playlist = servicePlaylist.findById(idPlaylist).get();
        List<Song> songList = (List<Song>) playlist.getSongs();
        List<Long> listIdSong = playlistAddDto.getIdSongs();
        for (int i = 0; i < listIdSong.size(); i++) {
            Song song = songService.findById(listIdSong.get(i)).get();
            if (song != null) {
                songList.add(song);
                List<Playlist> playlists = song.getPlaylists();
                playlists.add(playlist);
                songService.save(song);
            }
        }
        playlist.setSongs(songList);
        servicePlaylist.save(playlist);


        MesageRespons mesageRespons = new MesageRespons();
        mesageRespons.setMesage("ok");
        return new ResponseEntity<>(mesageRespons, HttpStatus.OK);
    }

    @PutMapping("/removeSong")
    public ResponseEntity<?> removeSong(@RequestBody RemoveSongDto removeSongDto) {
        Playlist playlist = servicePlaylist.findById(removeSongDto.getIdPlaylist()).get();
        Song song = songService.findById(removeSongDto.getIdSong()).get();
        List<Song> songList = (List<Song>) playlist.getSongs();

//        Xoa song khoi playlist
        List<Song> newSongList = new ArrayList<>();
        for (int i = 0; i < songList.size(); i++) {
            if (removeSongDto.getIdSong() != songList.get(i).getId()) {
                newSongList.add(songList.get(i));
            }
        }
        playlist.setSongs(newSongList);
        servicePlaylist.save(playlist);

//        Xoa song khoi playlist
        List<Playlist> playlists = song.getPlaylists();
        List<Playlist> newListPlaylist = new ArrayList<>();
        for (int i = 0; i < playlists.size(); i++) {
            if (removeSongDto.getIdPlaylist() != playlists.get(i).getId()) {
                newListPlaylist.add(playlists.get(i));
            }
        }

        song.setPlaylists(newListPlaylist);
        songService.save(song);
        MesageRespons mesageRespons = new MesageRespons();
        mesageRespons.setMesage("ok");
        return new ResponseEntity<>(mesageRespons, HttpStatus.OK);
    }

    @PostMapping("/playlistComment")
    public ResponseEntity<MesageRespons> createCommentForPlaylist(@RequestBody CommentDto comment) {
        Comment newComment = new Comment();
        Account account = serviceAccount.findById(comment.getIdAccount()).get();
        Playlist playlist = servicePlaylist.findById(comment.getIdPlaylist()).get();
        String text = comment.getText();
        newComment.setAccount(account);
        newComment.setPlaylist(playlist);
        newComment.setText(text);
        serviceComment.save(newComment);
        MesageRespons mesageRespons = new MesageRespons();
        mesageRespons.setMesage("ok");
        return new ResponseEntity<>(mesageRespons, HttpStatus.OK);
    }


    @PostMapping("/likePlaylist")
    public ResponseEntity<MesageRespons> createLikeForPlaylist(@RequestBody LikeDto likeDto) {
        Account account = serviceAccount.findById(likeDto.getIdAccount()).get();
        Playlist playlist = servicePlaylist.findById(likeDto.getIdPlaylist()).get();
        AccountLike like = new AccountLike();
        like.setAccount(account);
        like.setPlaylist(playlist);
        serviceLike.save(like);
        MesageRespons mesageRespons = new MesageRespons();
        mesageRespons.setMesage("ok");
        return new ResponseEntity<>(mesageRespons, HttpStatus.OK);
    }

    @DeleteMapping("/unlike/{idLike}")
    public ResponseEntity<?> unlikePlaylist(@PathVariable("idLike") Long idLike) {
        serviceLike.remove(idLike);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/deletePlaylist/{id}")
    public ResponseEntity<?> deletePlaylist(@PathVariable("id") Long id) {
        Playlist playlist = servicePlaylist.findById(id).get();
        if (playlist == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        servicePlaylist.remove(id);
        return new ResponseEntity<>(HttpStatus.OK);

    }
}
