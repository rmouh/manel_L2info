type movie = { 
    id : int;
    title : string;
    year : int;
    runtime : int;
    rank : int
}

type res = Movie of movie | Invalid | Eof

let input_movie in_c =
  try
    let s = input_line in_c in
    match String.split_on_char ';' s with
    [ s_id; title; s_year; s_runtime ; s_rank ] ->
       Movie ({
        id = int_of_string s_id;
        title = title;
        year = int_of_string s_year;
        runtime = int_of_string s_runtime;
        rank = int_of_string s_rank;
      })
    | _ -> Invalid

  with
   End_of_file -> Eof
  | _ -> Invalid


let load_movies f =
  let in_c = open_in f in
  let rec loop in_c acc =
    match input_movie in_c with
    | Eof -> acc
    | Invalid -> loop in_c acc
    | Movie m -> loop in_c (m :: acc)
  in
    let res = loop in_c [] in
    close_in in_c;
    res


let movies = load_movies "movies.csv"


let pr_movie m =
  Printf.printf "%s" ("{ id=" ^ string_of_int m.id ^ " ; title=" ^ m.title ^ "; year="^ string_of_int m.year ^"; runtime="^string_of_int m.runtime^"; rank="^ string_of_int m.rank ^"}\n")

let mov1 = { id=2004; title="The Good,the Bad and the Ugly"; year=1966; runtime=190; rank=5 }
(*test*********
let () = pr_movie mov
*)
let pr_movies ml = List.iter pr_movie ml

let  moviesTop10  ml = 
  let top = List.filter (fun x -> x.rank > 10) ml in 
  pr_movies top 


let mov2 = { id=2004; title="The Good,the Bad and the Ugly"; year=1966; runtime=190; rank=11 }
let mov3 = { id=2005; title="The Good,the Bad and the Ugly"; year=1966; runtime=190; rank=12 }
let mov4 = { id=2045; title="The Good,the Bad and the Ugly"; year=1966; runtime=190; rank=10 }
let mov5 = { id=200; title="The Good,the Bad and the Ugly"; year=1966; runtime=190; rank=0 }
let mov6 = { id=2004444; title="The Good,the Bad and the Ugly"; year=1966; runtime=190; rank=200 }

let tab = [mov1; mov2; mov3; mov4; mov5; mov6]


(*test*********
let () = moviesTop10 tab 
*)
let  moviesTop10  ml = List.filter (fun x -> x.year  >=1980 && x.year  <=1989) ml 

let f a x = 
  if a < x.id then x.id 
  else a

let max_id ml =
  if ml = [] then 0
  else List.fold_left f 0  ml

 (*test********* 
let () = Printf.printf "%d\n" (max_id tab) 
let () = Printf.printf "%d\n" (max_id []) 



let average_runtime ml =
  let somme =
     List.fold_left (fun a x -> a + x.runtime) 0 ml in 
     somme / (List.fold_left (+) 0 ml)


 test********* 
let () = Printf.printf "%d\n" (average_runtime tab) 
*)
let mycompare x1 x2 =
  if (fst x1) > (fst x2) then fst x1
  else fst x2

let average_by_year ml=
  let list = List.map  (fun x -> (x.year, x.runtime)) ml in 
    let mlsorted = List.sort mycompare list in
    let rec loop mlsorted accList = 
      match mlsorted with
      | [] -> accList
      | a::mlsorted -> 
        match accList with 
        | [] -> 
          ((fst a), (snd a)::[])::accList;
          loop mlsorted accList
        | b ::accList ->
          if compare (fst b) (fst a) = 0 then
            (snd a)::(snd b);
            loop mlsorted accList;
          else 
            ((fst a), (snd a)::[])::accList;
            loop mlsorted accList;
          in 7

      



    