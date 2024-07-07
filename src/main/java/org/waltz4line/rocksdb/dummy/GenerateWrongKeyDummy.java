package org.waltz4line.rocksdb.dummy;

import org.rocksdb.RocksDB;
import org.rocksdb.WriteOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.waltz4line.rocksdb.util.EndianUtil;
import org.waltz4line.rocksdb.util.WrongBinaryUtils;

import java.util.Random;

public class GenerateWrongKeyDummy {

    private static final Logger logger = LoggerFactory.getLogger(GenerateWrongKeyDummy.class);

    private static final int KEY_LEN = 7;

    private static final Random random = new Random();

    private final RocksDB rocksDB;

    private final byte prefix;

    private final byte[] initKey;

    public GenerateWrongKeyDummy(RocksDB rocksDB, byte prefix, byte[] initKey) {
        this.rocksDB = rocksDB;
        this.prefix = prefix;
        this.initKey = initKey;
    }

    public void generate(int records) {
        long initSeq = WrongBinaryUtils.convertToLong(initKey);
        long seq = initSeq + 1;
        logger.info("Previous Last Sequence: {}, Start Sequence: {}, Records:{}", initSeq, seq, records);
        try {
            for (int i = 0; i < records; i++) {
                byte[] key = new byte[KEY_LEN];
                EndianUtil.partialLongToBytes(key, seq++, 1, 7);
                key[0] = prefix;
                byte[] value = new byte[2048];
                random.nextBytes(value);
                rocksDB.put(key, value);
            }
            logger.info("Finish generate records: {}, Last Sequence: {}", records,  seq);
        }catch (Exception e) {
            logger.error("Failed to generate records. ", e);
        }
    }

}
