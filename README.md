# Dev

## Starting shadow-cljs

```bash
make shadow/dev
```

Connect to the shadow-cljs repl on port 41643

```clojure
(shadow/repl :app)
;; switch to core ns and run (server)
```

shadow-cljs logs are in `watch-log.txt`

## Killing shadow-cljs

```bash
make kill-shadow
```


