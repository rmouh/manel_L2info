let rec hanoi dep aux dest n =
  if n > 0 then begin
    hanoi dep dest aux (n-1);
    Printf.printf "%s -> %s\n" dep dest;
    hanoi aux dep dest (n - 1)
(*
    f =
    aux = dep
    fin = dep
    fin = aux
    *)
  end
 
let () = hanoi "A" "B" "C" 3
  