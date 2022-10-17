
let affiche_tab a =
  let rec affiche_tab_aux i a = 
    if i = Array.length a  then  
      Printf.printf ""
    else begin 
      Printf.printf "    %s\n" a.(i);
      affiche_tab_aux (i+1) a;
    end
  in affiche_tab_aux 0 a
(*let () =  affiche_tab Sys.argv
let () =  affiche_tab [|3; 0; 8; 5; 0; 0; 2; 0; 0; 1|]



open Printf
let affiche_tab a = 
  Array.iter (printf "    %d\n") a
*)
let rec affiche_dir d =
  if Sys.is_directory (d) then begin
    Printf.printf "%s : \n" d;
    let rep = Sys.readdir(d) in 
    affiche_tab rep;
    Sys.chdir(d);
    affiche_dir_iter 0 rep;
    Sys.chdir ".."
  end
and affiche_dir_iter i t =
  if i < Array.length t then begin 
    affiche_dir t.(i);
    affiche_dir_iter (i+1) t;
  end

let  () = affiche_dir  ("/mnt/c/Users/mouho/Desktop/gitHub/")