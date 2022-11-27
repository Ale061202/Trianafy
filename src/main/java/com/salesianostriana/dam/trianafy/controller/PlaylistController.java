package com.salesianostriana.dam.trianafy.controller;

import com.salesianostriana.dam.trianafy.dto.*;
import com.salesianostriana.dam.trianafy.model.Artist;
import com.salesianostriana.dam.trianafy.model.Playlist;
import com.salesianostriana.dam.trianafy.model.Song;
import com.salesianostriana.dam.trianafy.repos.ArtistRepository;
import com.salesianostriana.dam.trianafy.repos.PlaylistRepository;
import com.salesianostriana.dam.trianafy.repos.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class PlaylistController {

    private final PlaylistRepository repository;
    private final SongRepository repository2;
    private final ArtistRepository artistRepository;

    private final PlaylistDtoConverter dtoConverter;
    private final SongDtoConverter dtoConverter2;


    @GetMapping("/list")
    public ResponseEntity<List<GetPlaylistDto>> findAll(){
        List<GetPlaylistDto> result = new ArrayList();
        for (Playlist playList : repository.findAll()){
           result.add(dtoConverter.playlistToGetPlaylistDto(playList));
        }
        return  ResponseEntity.ok(result);
    }

    @GetMapping("/list/{id}")
    public ResponseEntity<Playlist> findById(@PathVariable Long id){
        return ResponseEntity.of(repository.findById(id));
    }

    @PostMapping("/list")
    public ResponseEntity<CreatePlaylistDto> createPlaylist(@RequestBody CreatePlaylistDto cs){
        Playlist p = dtoConverter.createPlaylistDtoToPlaylist(cs);
        repository.save(p);
        return ResponseEntity.status(HttpStatus.CREATED).body(cs);
    }

    @PutMapping("/list/{id}")
    public ResponseEntity<GetPlaylistDto> customPlaylist(@RequestBody GetPlaylistDto getPlaylistDto, @PathVariable Long id){
        Optional<Playlist> p = repository.findById(id);
        p.get().setName(getPlaylistDto.getName());
        repository.save(p.get());
        return ResponseEntity.of(Optional.of(getPlaylistDto));
        /*return ResponseEntity.of(repository.findById(id)
                .map(old -> {
                    old.setName(getPlaylistDto.getName());
                    return Optional.of(repository.save(old));
                }).orElse(Optional.empty())
        );*/
    }

    @DeleteMapping("/list/{id}")
    public ResponseEntity<Playlist> deletePlaylist(@PathVariable Long id){
        if (repository.existsById(id))
            repository.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/list/{id}/song")
    public ResponseEntity<Playlist> findSong(@PathVariable Long id){
        return ResponseEntity.of(repository.findById(id));
    }

    @GetMapping("/list/{id}/song/{id2}")
    public ResponseEntity<Song> findSongById(@PathVariable Long id, @PathVariable Long id2){
        Optional<Playlist> p = repository.findById(id);
        if (p.isPresent()){
            Playlist playlist = p.get();
            for (Song song : playlist.getSongs()) {
                if (song.getId() == id2){
                    return ResponseEntity.of(Optional.of(song));
                }
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping("/list/{id}/song/{id2}")
    public ResponseEntity<Playlist> addSongPlaylist(@PathVariable Long id, @PathVariable Long id2, @RequestBody CreateSongDto cs){
        Optional<Playlist> p = repository.findById(id);
        if (p.isPresent()){
            Playlist playlist = p.get();
            Optional<Song> s = repository2.findById(id2);
            if (s.isPresent()){
                playlist.addSong(s.get());
                repository.save(playlist);
                return ResponseEntity.ok(playlist);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/list/{id1}/song/{id2}")
    public ResponseEntity<Song> deleteSongById(@PathVariable Long id1, @PathVariable Long id2){
        Optional<Playlist> p = repository.findById(id1);
        if (p.isPresent()){
            Playlist playlist = p.get();
            for (Song song : playlist.getSongs()) {
                if (song.getId() == id2){
                    playlist.deleteSong(song);
                    repository.save(playlist);
                    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
                }
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
