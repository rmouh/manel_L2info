let rec somme_entiers n =
    if n <= 0 then 
        0
    else 
        n + somme_entiers(n - 1)
(*
let i = 0
let increase_counter c = c + 1
let rec somme_carres n =
    if i == n then 
        0
    else begin 
        increase_counter i;
        i*i + somme_carres(n)
    end
*)
let rec somme_carres n =
    if n == 0 then 
        0
    else begin 
        (n - 1)*(n - 1) + somme_carres(n - 1)
    end
        
let rec leibniz n =
    
let y = somme_entiers 6
let x = somme_carres 0
let () = Printf.printf "le nbr %d :" x
