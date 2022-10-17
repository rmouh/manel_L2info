let hanoi  n = 
  let rec f n deb aux fin =
    if n > 0 then begin 
      f (n-1) deb fin aux;
      Printf.printf "%s - > %s\n" deb fin;
      f (n-1) aux deb fin 
    end 
  in 
  f n "deb" "aux" "fin"
      
  
(*let () = hanoi 4 *)
type piquet = string * (int list) 
type jeu = piquet list 

(*let f e =
  Printf.printf "%d-" (e);
  if (e != [ ] ) then 
    Printf.printf "-" 
  else
    Printf.printf "%d" e 
*)
let f e =
  Printf.printf "%d-" (e)

let affiche_piquet p =
  Printf.printf "%s | " (fst p);
  let l_inv = List.rev (snd p) in
    List.iter  f l_inv;
  Printf.printf "\n"
;;
(*let () = affiche_piquet ("mid", [1;2;5])*)

(*let jeu = ([ ("dep", [2;3;4]); ("mid", []);  ("arr", [1]) ])
let choix_piquet j s = 
  let a = List.assoc s j in 
    let  piq = (s,a) in 
      affiche_piquet piq
      *)
let jeu = ([ ("dep", [2;3;4]); ("mid", []);  ("arr", [1]) ])

let choix_piquet j s = 
  let a = List.assoc s j in 
  (s,a)
      

(*let b  = choix_piquet  jeu "dep"*)
  
let affiche_jeu j =
  Printf.printf "\x1b[2J\x1b[H";
  affiche_piquet (choix_piquet j "dep");
  affiche_piquet (choix_piquet j "mid");
  affiche_piquet (choix_piquet j "arr");
  Printf.printf "%!"

(*let () = affiche_jeu jeu*)


(*let deplace_sommet p1 p2 =
  if snd p1 = [] then
    failwith "piquet vide"
  else if (List.hd (List.rev (fst p1))) > (List.hd (List.rev (fst p1))) then
    failwith "deplacement impossible"
  else begin 
    let s1 = List.hd (List.rev (fst p1))in 
    let np2 = append (snd p2) (List.hd (List.rev (fst p1))) in
    (List.rev (List. tl(List.rev (fst p1))) , np2)
  end
  *)
let deplace_sommet p1 p2 =
  let deplace_sommet_inv p1 p2 = 
  match p1, p2 with
  |(s1, l1), (s2, l2) -> 
    match l1, l2 with
    |[], _ -> failwith "piquet vide"
    |a::l, [] -> ((s1, List.rev (l)),(s2, List.rev(a::[])))
    |a::l11, b::l22 -> if (a > b) then failwith "deplacement impossible"
    else ((s1, l11),(s2, List.rev(a::l2)))
  in
  deplace_sommet_inv (fst p1,List.rev(snd p1)) (fst p2,List.rev(snd p2))
  
let jeue jeu s1 s2 s3 = 
  let p1 = choix_piquet jeu s1 in
  let p2 = choix_piquet jeu s2 in
  let p3 = choix_piquet jeu s3 in
  let p11, p22 = deplace_sommet p1 p22 in 
  [p11; p22; p3]


let rec pr_int_list l =
  match l with
  [ ] -> () (* ne rien faire *)
  | i :: ll ->
    Printf.printf "%d\n" i;
    pr_int_list ll

let gen_list n =
  let rec list_aux n l =
  if n=0 then l
  else list_aux (n-1) (n::l)
in list_aux n []

(*************test************
let () = pr_int_list (gen_list 9)
  *************test**************)

  let x,y =deplace_sommet ("dep", [2;3;4;8;9]) ("arr", [11])
  let ()= affiche_piquet x
  let ()= affiche_piquet y
 
let hanoi_list n =
   let rec hanoi_aux piquets dep mil arr n =
   if n > 0 then begin

    let h = hanoi_aux piquets dep arr mil (n-1);
    let j = joue piquets dep arr mil in
    hanoi_aux j mil dep arr (n-1)
end
    


   in
   let final =
   hanoi_aux ([ ("dep", gen_list n);("mil", []);("arr", []) ]) "dep" "mil" "arr" n
   in
   affiche_jeu final