package org.waltz4line.rocksdb;

import org.rocksdb.RocksDB;
import org.rocksdb.SstFileReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.waltz4line.rocksdb.util.FileUtil;
import org.waltz4line.rocksdb.util.SstFileReaderUtils;

import java.util.List;

public class SstReaderTest {

    private static final Logger logger = LoggerFactory.getLogger(SstReaderTest.class);

    public static void main(String[] args) throws Exception {
        RocksDB.loadLibrary();
        List<String> files = FileUtil.getSstFiles("./dummy");
        for (String filePath: files) {
            try (SstFileReader reader = SstFileReaderUtils.open(filePath)) {
                logger.info(filePath);
                SstFileReaderUtils.printKeys(reader);
            }
        }
    }
}
