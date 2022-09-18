let f  = 
    if Array.length (Sys.argv) != 2 then
        Printf.printf "Le fichier n'existe pas"
    else begin
        if Sys.file_exists(Sys.argv.(1)) then
            if Sys.is_directory(Sys.argv.(1)) then
                Printf.printf "Le répertoire existe" 
            else 
                Printf.printf "Le fichier existe"
        else
            Printf.printf "Le fichier n'existe pas"
    end
        
let () = f