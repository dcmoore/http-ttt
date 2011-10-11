(ns http-ttt.draw-board-response
	(:use [cloj_ttt_2.core])
	(:gen-class
		:name http-ttt.draw-board-response.MyResponse
		:extends com.cuvuligio.server.ServerResponse
		:prefix myResponse-))

(def request (ref (new java.util.HashMap)))

(defn get-request [key]
	(.get @request key))

(defn get-space [space-num]
	(cond
		(or (= "X" (get-request (str "Post-" space-num))) (= (str space-num) (get-request (str "Post-empty_space"))))
			(str "X<input type=\"hidden\" value=\"X\" name=\"" space-num "\" />")
		(= "O" (get-request (str "Post-" space-num)))
			(str "O<input type=\"hidden\" value=\"O\" name=\"" space-num "\" />")
		:else
			(str "<input type=\"radio\" value=\"" space-num "\" name=\"empty_space\" />")))

(defn draw-board []
	(str "<html><body>"
			"<form name=\"board\" action=\"/game\" method=\"post\">" ;action must be loaded with current params
				"<table><tr><td>"
					(get-space 1)
					"</td><td>"
					(get-space 2)
					"</td><td>"
					(get-space 3)
					"</td></tr><tr><td>"
					(get-space 4)
					"</td><td>"
					(get-space 5)
					"</td><td>"
					(get-space 6)
					"</td></tr><tr><td>"
					(get-space 7)
					"</td><td>"
					(get-space 8)
					"</td><td>"
					(get-space 9)
				"</td></tr></table>"
				"<input type=\"submit\" value=\"Make Move\" name=\"move\" />"
			"</form>"
			"<a href=\"/game?p=1&t=x\">New 1 Player Game: Team X</a><br />"
			"<a href=\"/game?p=1&t=o\">New 1 Player Game: Team O</a><br />"
			"<a href=\"/game?p=2\">New 2 Player Game</a>"
		"</body></html>"))

(defn myResponse-get [this req]
	(println req)
	(dosync (ref-set request req))
	(.getBytes (draw-board)))