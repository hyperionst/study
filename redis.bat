@echo on
NET STOP Redis
cd /
cd /4.DataBase/redis
redis-server redis.windows.conf