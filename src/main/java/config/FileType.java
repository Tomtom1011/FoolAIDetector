package config;

import domain.ImageHandler;
import domain.TextHandler;
import domain.TypeHandler;
import lombok.Getter;

@Getter
public enum FileType {
    IMAGE(new ImageHandler()),
    TEXT(new TextHandler());

    private final TypeHandler handler;

    FileType(TypeHandler handler) {
        this.handler = handler;
    }
}
