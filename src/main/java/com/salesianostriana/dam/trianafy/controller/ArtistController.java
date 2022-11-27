package com.salesianostriana.dam.trianafy.controller;

import com.salesianostriana.dam.trianafy.model.Artist;
import com.salesianostriana.dam.trianafy.model.Song;
import com.salesianostriana.dam.trianafy.repos.ArtistRepository;
import com.salesianostriana.dam.trianafy.service.ArtistService;
import com.salesianostriana.dam.trianafy.service.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ArtistController {

    private final ArtistService service;
    private final SongService songService;

    @GetMapping("/artist/")
    public ResponseEntity<List<Artist>> findAll(){
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/artist/{id}")
    public ResponseEntity<Artist> findById(@PathVariable Long id){
        return ResponseEntity.of(service.findById(id));
    }

    @PostMapping("/artist/")
    public ResponseEntity<Artist> createArtist(@RequestBody Artist artist){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.add(artist));
    }

    @PutMapping("/artist/{id}")
    public ResponseEntity<Artist> customArtist(@RequestBody Artist artist, @PathVariable Long id){
        return ResponseEntity.of(service.findById(id)
                .map(old -> {
                    old.setName(artist.getName());
                    return Optional.of(service.edit(old));
                })
                .orElse(Optional.empty())
        );
    }

    @DeleteMapping("/artist/{id}")
    public ResponseEntity<Artist> deleteArtist(@PathVariable Long id, Artist artist){
        List<Song> listSong = songService.findAll();
        for (Song song: listSong) {
            if (song.getArtist().getId() == id){
                song.setArtist(null);
            }
        }
        service.delete(artist);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
