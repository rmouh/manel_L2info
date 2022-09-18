let bissextile year =
	if year mod 4 == 0  then 
		if year mod 100 == 0 then
			if year mod 400 == 0 then 
				Printf.printf "l'annee est bissextille " 
			else
				Printf.printf "l'annee n'est pas bissextille " 
		else 
			Printf.printf "l'annee est bissextille "
	else
			Printf.printf "l'annee n'est pas bissextille "  

let () = bissextile 1992

let jour_mois m a = 
	