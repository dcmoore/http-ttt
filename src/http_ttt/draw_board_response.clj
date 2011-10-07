(ns http-ttt.draw-board-response
	(:use [cloj_ttt_2.core])
	(:gen-class
		:name http-ttt.draw-board-response.MyResponse
		:extends com.cuvuligio.server.ServerResponse
		:prefix myResponse-))

(defn draw-board []
	(str "ello mate!"))

(defn myResponse-get [this request]
	(.getBytes (draw-board)))