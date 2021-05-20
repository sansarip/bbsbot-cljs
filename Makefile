watch:
	npm run watch

node:
	node target/main.js

shadow/dev:
	touch watch-log.txt
	( make watch ) > watch-log.txt 2>&1 &
	tail -f -n0 watch-log.txt | grep -qe "Build completed"
	if [ $$? = 1 ]; then\
		echo "Error in build";\
		exit 1;\
	fi
	make node

kill-shadow:
	pkill -f shadow-cljs
