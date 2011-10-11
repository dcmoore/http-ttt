(ns http-ttt.core-spec
	(:use [speclj.core]
		[http-ttt.core])
	(:import (com.cuvuligio.server Server)))

(describe "Http-ttt.core"
	(it "starts and stops the server"
		(let [server (Server.)]
			(should= false (.isActive server))
			(start-server server)
			(Thread/sleep 900)  ;You might need to add some time to this sleep to get the test to pass on your machine
			(should= true (.isActive server))
			(stop-server server)
			(should= false (.isActive server))))
)