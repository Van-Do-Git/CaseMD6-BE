package com.codegym.casemd6.controller;

import com.codegym.casemd6.dto.SearchDto;
import com.codegym.casemd6.model.Comment;
import com.codegym.casemd6.model.Playlist;
import com.codegym.casemd6.model.Song;
import com.codegym.casemd6.service.comment.IServiceComment;
import com.codegym.casemd6.service.playlist.IServicePlaylist;
import com.codegym.casemd6.service.song.ISongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/client")
@EnableSpringDataWebSupport
public class ClientController {
    @Autowired
    ISongService songService;
    @Autowired
    IServiceComment serviceComment;
    @Autowired
    IServicePlaylist servicePlaylist;

    @GetMapping("/latest")
    public ResponseEntity<Page<Song>> latest() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id").descending());
        Page<Song> songPage = songService.findAllLestes(pageable);
        return new ResponseEntity<>(songPage, HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<Page<Song>> count() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("count").descending());
        Page<Song> songPage = songService.findAllLestes(pageable);
        return new ResponseEntity<>(songPage, HttpStatus.OK);
    }

    @GetMapping("/likes")
    public ResponseEntity<Page<Song>> likes() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("countLike").descending());
        Page<Song> songPage = songService.findAllLestes(pageable);
        return new ResponseEntity<>(songPage, HttpStatus.OK);
    }

    @PutMapping("/updateView")
    public ResponseEntity<String> updateSong(@RequestBody Long idSong) {
        Song song = songService.findById(idSong).get();
        Long count = song.getCount() + 1;
        song.setCount(count);
        System.out.println("luot nghi sau khi tang" + song.getCount());
        songService.save(song);
        return new ResponseEntity<>("Ok", HttpStatus.OK);
    }

    @GetMapping("/comment/{idSong}")
    public ResponseEntity<List<Comment>> fillAllComment(@PathVariable("idSong") Long idSong) {
        Song song = songService.findById(idSong).get();
        List<Comment> list = song.getCommentList();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/playlist/{id}")
    public ResponseEntity<?> findAllSongOfPlaylistById(@PathVariable("id") Long idPlaylist) {
        Playlist playlist = servicePlaylist.findById(idPlaylist).get();
        return new ResponseEntity<>(playlist, HttpStatus.OK);
    }

    @GetMapping("/playlists")
    public ResponseEntity<?> findAllPlaylistNew() {
        Pageable pageable = PageRequest.of(0, 8, Sort.by("id").ascending());
        Page<Playlist> playlistPage = servicePlaylist.findAllPlaylistLestes(pageable);
        List<Playlist> playlists = playlistPage.getContent();
        return new ResponseEntity<>(playlists, HttpStatus.OK);
    }

    @GetMapping("/playlistComment/{idPlaylist}")
    public ResponseEntity<List<Comment>> fillAllCommentOfPlaylist(@PathVariable("idPlaylist") Long idPlaylist) {
        Playlist playlist = servicePlaylist.findById(idPlaylist).get();
        List<Comment> list = playlist.getCommentList();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("/search")
    public ResponseEntity<List<Song>> findSongByNameOrSinger(@RequestBody SearchDto searchDto) {
        List<Song> songList;
        Pageable pageable = PageRequest.of(0, 8, Sort.by("id").ascending());
        if (searchDto.isNameOrSinger()) {
            songList = songService.findSongsByNameContaining(pageable, searchDto.getName()).getContent();
        } else {
            songList = songService.findSongsBySingerContaining(pageable, searchDto.getName()).getContent();
        }
        return new ResponseEntity<>(songList, HttpStatus.OK);
    }

}
