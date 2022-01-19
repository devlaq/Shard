package com.github.devlaq.shard.core;

import arc.Files;
import arc.backend.sdl.SdlFiles;
import arc.files.Fi;
import arc.util.ArcRuntimeException;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class ShardFi extends Fi {

    public ShardFi(String fileName, Files.FileType type) {
        super(fileName, type);
    }

    public ShardFi(File file, Files.FileType type) {
        super(file, type);
    }

    public Fi child(String name) {
        return this.file.getPath().length() == 0 ? new ShardFi(new File(name), this.type) : new ShardFi(new File(this.file, name), this.type);
    }

    public Fi sibling(String name) {
        if (this.file.getPath().length() == 0) {
            throw new ArcRuntimeException("Cannot get the sibling of the root.");
        } else {
            return new ShardFi(new File(this.file.getParent(), name), this.type);
        }
    }

    public File file() {
        if (this.type == Files.FileType.external) {
            return new File(SdlFiles.externalPath, this.file.getPath());
        } else {
            return this.type == Files.FileType.local ? new File(SdlFiles.localPath, this.file.getPath()) : this.file;
        }
    }

    @Override
    public InputStream read() {
        if (this.type != Files.FileType.classpath && (this.type != Files.FileType.internal || this.file().exists()) && (this.type != Files.FileType.local || this.file().exists())) {
            try {
                return new FileInputStream(this.file());
            } catch (Exception var2) {
                if (this.file().isDirectory()) {
                    throw new ArcRuntimeException("Cannot open a stream to a directory: " + this.file + " (" + this.type + ")", var2);
                } else {
                    throw new ArcRuntimeException("Error reading file: " + this.file + " (" + this.type + ")", var2);
                }
            }
        } else {
            InputStream input = ShardFi.class.getResourceAsStream("/" + this.file.getPath().replace('\\', '/'));
            if (input == null) {
                throw new ArcRuntimeException("File not found: " + this.file + " (" + this.type + ")");
            } else {
                return input;
            }
        }
    }

    @Override
    public boolean exists() {
        switch(this.type) {
            case internal:
                if (this.file().exists()) {
                    return true;
                }
            case classpath:
                return ShardFi.class.getResource("/" + this.file.getPath().replace('\\', '/')) != null;
            default:
                return this.file().exists();
        }
    }



}
