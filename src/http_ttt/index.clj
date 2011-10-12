(ns http-ttt.index
	(:gen-class
		:name http-ttt.index.IndexResponse
		:extends com.cuvuligio.server.ServerResponse
		:prefix indexResponse-))

(defn indexResponse-get [this req]
	(.getBytes 
		(str "<html><head><meta http-equiv=\"Refresh\" content=\"0;url=/game?p=2&t=N\" /></head><body><p>You are being directed to the <a href =\"/game?p=1&t=X\">Tic Tac Toe Game</a></p></body></html>")))