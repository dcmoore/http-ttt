(ns http-ttt.draw-board-response
	(:use [cloj_ttt_2.core])
	(:gen-class
		:name http-ttt.draw-board-response.MyResponse
		:extends com.cuvuligio.server.ServerResponse
		:prefix myResponse-))

(defn space-to-str [space-num board]
	(cond
		(= x (get board space-num))
			(str "X<input type=\"hidden\" value=\"1\" name=\"" space-num "\" />")
		(= o (get board space-num))
			(str "O<input type=\"hidden\" value=\"2\" name=\"" space-num "\" />")
		:else
			(if (over? board)
				"&nbsp;"
				(str "<input type=\"radio\" value=\"" space-num "\" name=\"empty_space\" />"))))

(defn page-to-str [board request]
	(str "<html><body>"
			"<form name=\"board\" action=\"/game?p=" (.get request "Param-p") "&t=" (.get request "Param-t") "\" method=\"post\">"
				"<table><tr><td>"
					(space-to-str 0 board)
					"</td><td>"
					(space-to-str 1 board)
					"</td><td>"
					(space-to-str 2 board)
					"</td></tr><tr><td>"
					(space-to-str 3 board)
					"</td><td>"
					(space-to-str 4 board)
					"</td><td>"
					(space-to-str 5 board)
					"</td></tr><tr><td>"
					(space-to-str 6 board)
					"</td><td>"
					(space-to-str 7 board)
					"</td><td>"
					(space-to-str 8 board)
				"</td></tr></table>"
				"<input type=\"submit\" value=\"Make Move\" name=\"move\" />"
			"</form>"
			; "<a href=\"/game?p=1&t=X\">New 1 Player Game: Team X</a><br />"
			; "<a href=\"/game?p=1&t=O\">New 1 Player Game: Team O</a><br />"
			"<a href=\"/game?p=2\">New 2 Player Game</a>"
		"</body></html>"))

(defn go-through-prev-moves [request]
	(loop [space (dec board-size)
			board {}]
		(if (not= -1 space)
			(recur
				(dec space)
				(if (not= nil (.get request (str "Post-" space)))
					(assoc board space (Integer/parseInt (.get request (str "Post-" space))))
					board))
			board)))

(defn populate-board [request]
	(let [board (go-through-prev-moves request)
			move (.get request "Post-empty_space")]
		(if (not= move nil)
			(assoc board (Integer/parseInt move) (current-team board))
			board)))

(defn myResponse-get [this request]
	(cond
		(and (= "1" (.get request "Param-p")) (= "X" (.get request "Param-t")))
			;TODO
		(and (= "1" (.get request "Param-p")) (= "O" (.get request "Param-t")))
			;TODO
		:else
			(.getBytes (page-to-str
				(populate-board request)
				request))))