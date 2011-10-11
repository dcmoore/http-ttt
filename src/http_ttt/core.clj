(ns http-ttt.core
	(:gen-class)
	(:import (com.cuvuligio.server Server)))

(defn start-server [server]
	(doto
		server
		(.addRoute "/" (new http-ttt.index.IndexResponse))
		(.addRoute "/game" (new http-ttt.draw-board-response.MyResponse))
		(.start)))

(defn stop-server [server]
	(doto
		server
		(.gracefulKill)))

(defn run-game []
	(let [server (Server.)]
		(start-server server)))

(defn -main [& args]
	(run-game)
	(println "**********Tic Tac Toe**********"))