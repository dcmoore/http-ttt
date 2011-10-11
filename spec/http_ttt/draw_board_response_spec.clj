(ns http-ttt.draw-board-response-spec
	(:use [speclj.core]
		[http-ttt.draw-board-response]))

(describe "Http-ttt.draw-board-response"
	(it "displays the board"
		(binding [get-space (fn [& _] "test")]
			(should= "<html><body><form name=\"board\" action=\"/\" method=\"post\"><table><tr><td>test</td><td>test</td><td>test</td></tr><tr><td>test</td><td>test</td><td>test</td></tr><tr><td>test</td><td>test</td><td>test</td></tr></table><input type=\"submit\" value=\"Make Move\" name=\"move\" /></form><a href=\"/?p=1&t=x\">New 1 Player Game: Team X</a><br /><a href=\"/?p=1&t=o\">New 1 Player Game: Team O</a><br /><a href=\"/?p=2\">New 2 Player Game</a></body></html>"
				(draw-board))))
	
	(it "gets the space"
		(binding [get-request (fn [& _] "x")]
			(should= "X" (get-space 1)))
		(binding [get-request (fn [& _] "o")]
			(should= "O" (get-space 1)))
		(binding [get-request (fn [& _] nil)]
			(should= "<input type=\"radio\" value=\"1\" name=\"space\" />" (get-space 1))))
)