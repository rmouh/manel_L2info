(*let rec guess n =
  Printf.printf "entrer un nbr :";
  let b = read_int () in
  if n = b then Printf.printf "Trouvee" 
  else begin 
    if b > n then Printf.printf "tres grand !" 
    else 
      Printf.printf "tres petit !\n";
    guess n
  end 

  let () =  guess (int_of_string Sys.argv.(1))*)
  
  let rec guess2 n i =
    Printf.printf "entrer un nbr :"
    let b = read_int () in
    if m = b then Printf.printf "Trouvee apres %d essaie " i
    else 
      begin 
        if  b > m then 
          Printf.printf "tres grand !\n"
        else 
          Printf.printf "tres petit !\n";
        guess2 m i+1;
      end 
let () =
  let () = Random.self_init () in 
    let nb = Random.int 100 in 
      guess2 nb 1