/*
 * Copyright (c) 2018 - 2030, LanRuiTongDa Co.,Ltd
 * All rights reserved. 
 */

package com.tuspass.realname.common.utils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 唯一ID发生器
 */
public enum UniqueId {

    Iot_Device("1", "0") {
        private Utils utils = new UniqueId.Utils();

        @Override
        public String generate() {
            long[] factors;
            synchronized (this) {
                factors = utils.next();
            }
            long milliseconds = factors[0];
            long sequences = factors[1];

            // 获得与自定义原点的毫秒数差值
            milliseconds -= originClock;
            // 拼装成唯一的12位时间种子
            String id = String.format("%012d", milliseconds);
            // 产生大于等于0小于100之间的伪随机数，用于取模
            int num = (int) (Math.random() * 100);
            // 拼装时空因子
            id = String.format("%s%s%02d%s%02d%02d",
                    type,
                    subType,
                    clusterId,
                    id,
                    sequences,
                    num);
            // 此处没有迭代以混淆成无序
            return id;
        }
    },

    Pos_Device("2", "0") {
        private Utils utils = new UniqueId.Utils();

        @Override
        public String generate() {
            long[] factors;
            synchronized (this) {
                factors = utils.next();
            }
            long milliseconds = factors[0];
            long sequences = factors[1];

            String date = sdf.format(milliseconds);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(milliseconds);
            // 回退到当天0点
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            // 获得与当天0点的毫秒数差值
            milliseconds -= calendar.getTimeInMillis();
            // 拼装成唯一的10位种子
            String id = String.format("%08d%02d", milliseconds, sequences);
            // 拼装时空因子
            id = String.format("%s%s%02d%s%s",
                    type,
                    subType,
                    clusterId,
                    date,
                    id);
            // 两轮迭代以混淆成无序
            id = Utils.iterate(id, 14);
            id = Utils.iterate(id, 10);
            return id;
        }
    },

    Transaction("3", "0") {
        private Utils utils = new UniqueId.Utils();

        @Override
        public String generate() {
            long[] factors;
            synchronized (this) {
                factors = utils.next();
            }
            long milliseconds = factors[0];
            long sequences = factors[1];

            String date = sdf.format(milliseconds);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(milliseconds);
            // 回退到当天0点
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            // 获得与当天0点的毫秒数差值
            milliseconds -= calendar.getTimeInMillis();
            // 拼装成唯一的10位种子
            String id = String.format("%08d%02d", milliseconds, sequences);
            // 拼装时空因子
            id = String.format("%s%s%02d%s%s",
                    type,
                    subType,
                    clusterId,
                    date,
                    id);
            // 两轮迭代以混淆成无序
            id = Utils.iterate(id, 14);
            id = Utils.iterate(id, 10);
            return id;
        }
    },

    Runtime_Log("4", "1") {
        private Utils utils = new UniqueId.Utils();

        @Override
        public String generate() {
            long[] factors;
            synchronized (this) {
                factors = utils.next();
            }
            long milliseconds = factors[0];
            long sequences = factors[1];

            String date = sdf.format(milliseconds);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(milliseconds);
            // 回退到当天0点
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            // 获得与当天0点的毫秒数差值
            milliseconds -= calendar.getTimeInMillis();
            // 拼装成唯一的10位种子
            String id = String.format("%08d%02d", milliseconds, sequences);
            // 拼装时空因子
            id = String.format("%s%s%02d%s%s",
                    type,
                    subType,
                    clusterId,
                    date,
                    id);
            // 两轮迭代以混淆成无序
            id = Utils.iterate(id, 14);
            id = Utils.iterate(id, 10);
            return id;
        }
    },
    ;

    final String type;
    final String subType;

    /**
     * 构造器
     *
     * @param type    1位数字标识产生的ID类型
     * @param subType 1位数字标识产生的ID子类型
     */
    UniqueId(String type, String subType) {
        this.type = type;
        this.subType = subType;
    }

    public abstract String generate();

    private static final long originClock = 1533052800000L;// 2018-08-01 00:00:00.0
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
    private static final int threshold = 1000000;
    private static final int[] substitution = new int[threshold];
    private static final int clusterId;

    static {
        String tag = UniqueId.class.getSimpleName() + " - ";
        try {
            String cluster = currentClusterId();
            int length = cluster.length();
            if (length <= 0 || length > 2) {
                throw new Error(tag + "The length of 'cluster.id' isn't supported!");
            }
            for (int i = 0; i < length; i++) {
                char character = cluster.charAt(i);
                if (character < '0' && character > '9') {
                    throw new Error(tag + "The 'cluster.id' is illegal!");
                }
            }
            clusterId = Integer.valueOf(cluster);
        } catch (Exception e) {
            throw new Error(tag + "Can't get 'cluster.id'!");
        }
        BufferedReader br = null;
        try {
            String path = "/substitution.data";
            InputStream is = UniqueId.class.getResourceAsStream(path);
            br = new BufferedReader(new InputStreamReader(is));
            String line = br.readLine();
            for (int i = 0; i < threshold; i++) {
                substitution[i] = Integer.parseInt(line);
                line = br.readLine();
            }
        } catch (Exception e) {
            throw new Error(tag + "Read 'substitution.data' error!");
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    throw new Error(tag + "Close 'substitution.data' error!");
                }
            }
        }
    }

    /**
     * 文件优先级最高，环境变量优先级次之，jvm参数最低
     */
    private static String currentClusterId() {
        System.out.println("尝试获取clusterId，" + DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        String cid = null;
        boolean isNumber = false;
        if (!isNumber) {
            // 从~/.cluster.id文件
            System.out.println("尝试从文件获取clusterId");
            String userHome = System.getProperty("user.home");
            System.out.println("userHome:" + userHome);
            File file = FileUtils.getFile(userHome, ".cluster.id");
            if (file == null) {
                System.out.println("文件为null");
            } else {
                System.out.println("file path:" + file.getPath());
                if (!file.exists()) {
                    System.out.println("文件不存在");
                } else {
                    try {
                        cid = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
                        cid = StringUtils.trim(cid);
                        System.out.println("从文件读取的clusterId=" + cid);
                        isNumber = NumberUtils.isDigits(cid);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        if (!isNumber) {
            // 从环境变量
            cid = System.getenv("CLUSTER_ID");
            System.out.println("从环境变量中获取，clusterId=" + cid);
            isNumber = NumberUtils.isDigits(cid);
        }
        if (!isNumber) {
            // 从JVM参数
            cid = System.getProperty("cluster.id");
            System.out.println("从jvm参数获取clusterId=" + cid);
            isNumber = NumberUtils.isDigits(cid);
        }
        if (!isNumber) {
            System.out.println("未指定clusterId, 使用默认值99");
            cid = "99";
        }
        System.out.println("最终使用clusterId: " + cid);
        return cid;
    }

    public static class Utils {
        private int sequence = 0;
        private long lastMillisecond = -1L;
        private static final int count = 6;

        public static String iterate(String id, int off) {
            if (id != null) {
                int length = id.length();
                // 只支持百万级数迭代
                if (count <= (length - off)) {
                    String prefix = id.substring(0, off);
                    String suffix = id.substring(off + count, length);
                    int index = substitution[Integer.valueOf(id.substring(off, off + count))];
                    return String.format("%s%06d%s", prefix, index, suffix);
                }
            }
            return id;
        }

        private long[] next() {
            long millisecond = System.currentTimeMillis();
            if (millisecond == lastMillisecond) {
                // 同一毫秒内使用sequence进行分散,支持100个
                sequence++;
                if (sequence == MAX_SEQUENCE) {
                    // 等待到下一毫秒
                    do {
                        millisecond = System.currentTimeMillis();
                    } while (millisecond <= lastMillisecond);
                    sequence = 0;
                    lastMillisecond = millisecond;
                }
            } else {
                sequence = 0;
                lastMillisecond = millisecond;
            }
            return new long[]{millisecond, sequence};
        }

        private final int MAX_SEQUENCE = 100;
    }

}
