(ns http-ttt.draw-board-response-spec
	(:use [speclj.core]
		[cloj_ttt_2.core]
		[http-ttt.draw-board-response]))

(describe "Http-ttt.draw-board-response"
	(with test-request (java.util.HashMap.))
	
	(before
		(doto @test-request
			(.put "Param-p" "2")
			(.put "Param-t" "N")
			(.put "Post-0" "1")
			(.put "Post-1" "2")
			(.put "Post-empty_space" "4")))
		
	(it "gets the space"
		(should= "X<input type=\"hidden\" value=\"1\" name=\"0\" />" (space-to-str 0 {0 1, 1 2, 4 1}))
		(should= "<input type=\"radio\" value=\"2\" name=\"empty_space\" />" (space-to-str 2 {0 1, 1 2, 4 1}))
		(should= "O<input type=\"hidden\" value=\"2\" name=\"1\" />" (space-to-str 1 {0 1, 1 2, 4 1}))
		(should= "&nbsp;" (space-to-str 3 {0 1, 1 2, 4 1, 2 2, 8 1})))
	
	(it "gets the board"
		(should= "<html><body><form name=\"board\" action=\"/game?p=2&t=N\" method=\"post\"><table><tr><td>X<input type=\"hidden\" value=\"1\" name=\"0\" /></td><td>O<input type=\"hidden\" value=\"2\" name=\"1\" /></td><td><input type=\"radio\" value=\"2\" name=\"empty_space\" /></td></tr><tr><td><input type=\"radio\" value=\"3\" name=\"empty_space\" /></td><td>X<input type=\"hidden\" value=\"1\" name=\"4\" /></td><td><input type=\"radio\" value=\"5\" name=\"empty_space\" /></td></tr><tr><td><input type=\"radio\" value=\"6\" name=\"empty_space\" /></td><td><input type=\"radio\" value=\"7\" name=\"empty_space\" /></td><td><input type=\"radio\" value=\"8\" name=\"empty_space\" /></td></tr></table><input type=\"submit\" value=\"Make Move\" name=\"move\" /></form><a href=\"/game?p=1&t=X\">New 1 Player Game: Team X</a><br /><a href=\"/game?p=1&t=O\">New 1 Player Game: Team O</a><br /><a href=\"/game?p=2&t=N\">New 2 Player Game</a></body></html>" (page-to-str {0 1, 1 2, 4 1} @test-request))
		(should= "<html><body><form name=\"board\" action=\"/game?p=2&t=N\" method=\"post\"><table><tr><td>X<input type=\"hidden\" value=\"1\" name=\"0\" /></td><td>O<input type=\"hidden\" value=\"2\" name=\"1\" /></td><td>O<input type=\"hidden\" value=\"2\" name=\"2\" /></td></tr><tr><td>&nbsp;</td><td>X<input type=\"hidden\" value=\"1\" name=\"4\" /></td><td>&nbsp;</td></tr><tr><td>&nbsp;</td><td>&nbsp;</td><td>X<input type=\"hidden\" value=\"1\" name=\"8\" /></td></tr></table><input type=\"submit\" value=\"Make Move\" name=\"move\" /></form><a href=\"/game?p=1&t=X\">New 1 Player Game: Team X</a><br /><a href=\"/game?p=1&t=O\">New 1 Player Game: Team O</a><br /><a href=\"/game?p=2&t=N\">New 2 Player Game</a></body></html>" (page-to-str {0 1, 1 2, 4 1, 2 2, 8 1} @test-request)))
	
	(it "loops through previous moves and returns a board"
		(should= {0 1, 1 2} (go-through-prev-moves @test-request))
		(let [new-test-request (doto @test-request (.put "Post-3" "1"))]
			(should= {0 1, 1 2, 3 1} (go-through-prev-moves new-test-request))))
	
	(it "gets all prev moves and adds a new move if there is one"
		(should= {4 1, 0 1, 1 2} (populate-board @test-request))
		(let [new-test-request (doto @test-request (.put "Post-empty_space" "5"))]
			(should= {5 1, 0 1, 1 2} (populate-board @test-request))))
	
	(it "gets a byte array representing the response"
		(should= "<html><body><form name=\"board\" action=\"/game?p=2&t=N\" method=\"post\"><table><tr><td>X<input type=\"hidden\" value=\"1\" name=\"0\" /></td><td>O<input type=\"hidden\" value=\"2\" name=\"1\" /></td><td><input type=\"radio\" value=\"2\" name=\"empty_space\" /></td></tr><tr><td><input type=\"radio\" value=\"3\" name=\"empty_space\" /></td><td>X<input type=\"hidden\" value=\"1\" name=\"4\" /></td><td><input type=\"radio\" value=\"5\" name=\"empty_space\" /></td></tr><tr><td><input type=\"radio\" value=\"6\" name=\"empty_space\" /></td><td><input type=\"radio\" value=\"7\" name=\"empty_space\" /></td><td><input type=\"radio\" value=\"8\" name=\"empty_space\" /></td></tr></table><input type=\"submit\" value=\"Make Move\" name=\"move\" /></form><a href=\"/game?p=1&t=X\">New 1 Player Game: Team X</a><br /><a href=\"/game?p=1&t=O\">New 1 Player Game: Team O</a><br /><a href=\"/game?p=2&t=N\">New 2 Player Game</a></body></html>" (String. (myResponse-get "" @test-request))))
	
	(it "makes a computer move when it is the computer's turn"
		(should= {} (make-computer-move {} @test-request))
		(let [new-test-request (doto @test-request (.put "Param-p" "1") (.put "Param-t" "X"))]
			(should= {0 2, 1 1} (make-computer-move {1 1} @test-request))
			(should= {0 2, 1 1} (make-computer-move {0 2, 1 1} @test-request)))
		(let [new-test-request (doto @test-request (.put "Param-p" "1") (.put "Param-t" "O"))]
			(should= {1 1} (make-computer-move {1 1} @test-request))
			(should= {3 1, 0 2, 1 1} (make-computer-move {0 2, 1 1} @test-request))))
)