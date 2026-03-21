#!/vendor/bin/sh
#
# HBM bridge: translates simple 0/1 writes from HbmService
# into disp_param format for Xiaomi mi_display interface.
#

CTRL=/data/vendor/display/hbm_mode
DISP=/sys/devices/virtual/mi_display/disp_feature/disp-DSI-0/disp_param

mkdir -p /data/vendor/display
echo 0 > $CTRL
chown system:system $CTRL
chmod 0644 $CTRL

PREV="0"
while true; do
    CUR=$(cat $CTRL 2>/dev/null)
    if [ "$CUR" != "$PREV" ]; then
        if [ "$CUR" = "1" ]; then
            echo "1 1" > $DISP
        else
            echo "1 0" > $DISP
        fi
        PREV=$CUR
    fi
    sleep 1
done
