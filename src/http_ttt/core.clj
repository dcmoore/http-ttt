(ns http-ttt.core
	(:gen-class)
	(:import (com.cuvuligio.server Server)))
		
(defn run-server []
	(doto
		(Server.)
		(.addRoute "/test" (new http-ttt.draw-board-response.MyResponse))
		(.run)))

(defn -main [& args]
	(run-server))