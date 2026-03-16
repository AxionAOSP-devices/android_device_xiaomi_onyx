/*
 * Copyright (C) 2021 The LineageOS Project
 *
 * SPDX-License-Identifier: Apache-2.0
 */

#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/ioctl.h>
#include <unistd.h>

#define CMD_DATA_BUF_SIZE 256
#define COMMON_DATA_CMD 0
#define SELECT_TOUCH_ID 3
#define SET_CUR_VALUE 0
#define TOUCH_DOUBLETAP_MODE 14
#define TOUCH_MAGIC 0x54
#define TOUCH_DEV_PATH "/dev/xiaomi-touch"
#define TOUCH_ID 0

typedef struct {
    int8_t   touch_id;
    uint8_t  cmd;
    uint16_t mode;
    uint16_t data_len;
    int32_t  data_buf[CMD_DATA_BUF_SIZE];
} touch_data;

#define TOUCH_IOC_COMMON_DATA _IOW(TOUCH_MAGIC, COMMON_DATA_CMD, touch_data)
#define TOUCH_IOC_SELECT_TOUCH_ID _IOW(TOUCH_MAGIC, SELECT_TOUCH_ID, int)

int main(int argc, char *argv[]) {
    if (argc < 2) {
        fprintf(stderr, "Usage: %s <0|1>\n", argv[0]);
        return 1;
    }

    int enable = atoi(argv[1]);

    int fd = open(TOUCH_DEV_PATH, O_RDWR);
    if (fd < 0) {
        perror("open xiaomi-touch");
        return 1;
    }

    ioctl(fd, TOUCH_IOC_SELECT_TOUCH_ID, TOUCH_ID);

    touch_data data;
    memset(&data, 0, sizeof(data));
    data.touch_id = TOUCH_ID;
    data.cmd = SET_CUR_VALUE;
    data.mode = TOUCH_DOUBLETAP_MODE;
    data.data_len = 1;
    data.data_buf[0] = enable ? 1 : 0;
    ioctl(fd, TOUCH_IOC_COMMON_DATA, &data);

    close(fd);
    return 0;
}
