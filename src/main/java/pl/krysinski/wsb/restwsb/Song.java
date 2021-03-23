package pl.krysinski.wsb.restwsb;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Song {

    Long id;
    String title;
    String author;

    public Song(String title, String author) {
        this.title = title;
        this.author = author;
    }
}
