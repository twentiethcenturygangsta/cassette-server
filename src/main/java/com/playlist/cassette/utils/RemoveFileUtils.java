package com.playlist.cassette.utils;

import java.io.File;

public abstract class RemoveFileUtils {
    public static String removeFile(File targetFile) {
        if (targetFile.delete()) {
            return targetFile.getName() + " 파일이 삭제되었습니다.";
        } else {
            return targetFile.getName() + " 파일이 삭제되지 못했습니다.";
        }
    }
}
