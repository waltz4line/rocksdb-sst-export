package org.waltz4line.rocksdb.util;

import org.rocksdb.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.waltz4line.rocksdb.util.RocksdbUtil.newOptions;

public class SstFileReaderUtils {

    private static final Logger logger = LoggerFactory.getLogger(SstFileReaderUtils.class);

    public static SstFileReader open(String sstFilePath) throws RocksDBException {
        SstFileReader reader = new SstFileReader(newOptions());
        reader.open(sstFilePath);
        return reader;
    }

    public static void printKeys(SstFileReader reader) {
        logger.info("==================== start print keys =====================");
        int sstFileReadCount = 0;
        try(ReadOptions readOptions = new ReadOptions()) {
            try(SstFileReaderIterator iterator = reader.newIterator(readOptions)) {
                iterator.seekToFirst();
                while (iterator.isValid()) {
                    sstFileReadCount++;
                    logger.info(HexUtil.toHexString(iterator.key()));
                    iterator.next();
                }
            }
        }
        logger.info("Read count:{}", sstFileReadCount);
        logger.info("==================== finish print keys =====================");
    }

}
