(ns http-ttt.core-spec
	(:use [speclj.core]
		[http-ttt.core]))

(describe "Http-ttt.core"
	(it "runs the server"
		(should= true (run-server)))
)