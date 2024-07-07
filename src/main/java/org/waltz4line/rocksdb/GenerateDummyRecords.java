package org.waltz4line.rocksdb;

import org.rocksdb.ReadOptions;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;
import org.rocksdb.RocksIterator;
import org.waltz4line.rocksdb.dummy.GenerateWrongKeyDummy;
import org.waltz4line.rocksdb.util.RocksdbUtil;

public class GenerateDummyRecords {

    public static void writeRecords(RocksDB rocksDB, int count)  {
        byte[] initKey = new byte[7];

        try (RocksIterator it = rocksDB.newIterator(new ReadOptions())) {
            it.seekToLast();
            if (it.isValid()) {
                initKey = it.key();
            }
        }

        GenerateWrongKeyDummy records = new GenerateWrongKeyDummy(rocksDB, (byte) 0x02, initKey);
        records.generate(count);

    }

    public static void main(String[] args) throws Exception {
        RocksDB.loadLibrary();
        try (RocksDB rocksDB = RocksdbUtil.openRocksdb("./dummy")) {
//        writeRecords(rocksDB, 200);
            RocksdbUtil.printKeys(rocksDB);
        }
    }

}
