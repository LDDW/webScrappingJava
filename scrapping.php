<?php
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $command = 'java -jar out/artifacts/Scrapper2_jar/Scrapper2.jar';
    exec($command, $output, $return_value);

    // Vérifiez le résultat de l'exécution
    if ($return_value !== 0) {
        echo "Une erreur s'est produite lors de l'exécution du bot Java.";
    } else {
        $resultFile = 'out/artifacts/Scrapper2_jar/Resultat/result.txt';
        $resultContent = file_get_contents($resultFile);
    }
}
?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>scrapping</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="p-4">
    <textarea class="bg-gray-50 border w-full h-[50vh] resize-none" name="content" id="" cols="30" rows="10"><?php echo $resultContent ?? ''; ?></textarea>
    <form method="post">
        <button type="submit" class="bg-blue-500 text-white px-5 py-2 rounded-sm">Lancer le scrapping</button>
    </form>
</body>
</html>
