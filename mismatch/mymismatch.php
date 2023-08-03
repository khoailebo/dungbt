<?php
    require_once('appvars.php');
    require_once('connectvars.php');
    require_once('setsession.php');
    if(!isset($_SESSION['user_id']))
    {
        echo 'You have to <a href = "login.php">login</a> first!';
        exit();
    }
    $title_ms = "Your mismatcher!";
    require_once('header.php');
    require_once('nav.php');

    $user_reponses = array();
    $dbc = mysqli_connect(DB_HOST,DB_USER,DB_PASSWORD,DB_NAME);
    $query = "select mr.reponse, mt.name as topic_name,mc.name as category_name ".
    "from mismatch_response as mr ".
    "inner join mismatch_topic as mt using(topic_id) ".
    "inner join mismatch_category as mc using(category_id)".
    "where user_id = ".$_SESSION['user_id'];
    $data = mysqli_query($dbc,$query);
    if(mysqli_num_rows($data) > 0)
    {
        while($row = mysqli_fetch_array($data))
        {
            $user_reponses[] = $row;
        }
    }
    else 
    {
        echo 'You have to fill in questionare first';
        exit();
    }
    $query = "select user_id from mismatch_user where user_id != ".$_SESSION['user_id'];
    $data = mysqli_query($dbc,$query);

    $mismatch_user_id = -1;
    $best_mismatch_score = 0;
    $mismatch_topics = array();
    while($row = mysqli_fetch_array($data))
    {
        $score = 0;
        $mismatch_response = array();
        $topics = array();
        $category = array();
        $query2 = "select reponse from mismatch_response where user_id = ". $row['user_id'];
        $data2 = mysqli_query($dbc,$query2);
        while($row2 = mysqli_fetch_array($data2))
        {
            $mismatch_response[] = $row2;
        }
        for($i = 0;$i < count($user_reponses);$i++)
        {
            if(((int)$user_reponses[$i]['reponse'] + (int)$mismatch_response[$i]['reponse']) == 3)
            {
                $score++;
                $topics[] = $user_reponses[$i]['topic_name'];
                $category[] = $user_reponses[$i]['category_name'];
            }
            if($score > $best_mismatch_score)
            {
                $best_mismatch_score = $score;
                $mismatch_user_id = $row['user_id'];
                $mismatch_topics = array_slice($topics,0);
            }
        }
    }
    $category_total = array(array($category[0],0));
    foreach($category as $item)
    {
        if($item == $category_total[count($category_total) - 1][0])
        {
            $category_total[count($category_total) - 1][1]++;
        }
        else
        {
            $category_total[] = array($item,1);
        }
    }
    function createBarGraph($data_arr){
        $Width = 400;
        $Height = 300;
        $img_width = 500;
        $img_height = 400;
        $img = imagecreatetruecolor($Width,$Height);
        
        $red = imagecolorallocate($img,200,0,0);
        $white = imagecolorallocate($img,255,255,255);
        $black = imagecolorallocate($img,0,0,0);

        imagefilledrectangle($img,0,0,$Width,$Height,$white);
        $bar_Width = $Width / (2 * count($data_arr) + 1);
        $start_x = $bar_Width;
        $start_y = $Height;
        for($i = 0;$i < count($data_arr);$i++)
        {
            $end_x = $start_x + $bar_Width;
            $end_y = $Height - $Height / 5 *  ($data_arr[$i][1]);
            imagefilledrectangle($img,$start_x,$start_y,$end_x,$end_y,$red);
            imagestringup($img,5,$start_x + 5,$start_y - 5,$data_arr[$i][0],$white);
            $start_x += 2 * $bar_Width;
        }
        imagerectangle($img,0,0,$Width-1,$Height-1,$black);
        for($i = 1;$i <= 5;$i++)
        {
            imagestring($img,5,5, $Height - $Height / 5 *  $i,''.$i,$black );
        }
        imagepng($img,MM_UPLOADPATH.$_SESSION['user_id'].'_bargraph.png',5);
        imagedestroy($img);
    }
    createBarGraph($category_total);
    //display mismatch
    if($mismatch_user_id != -1)
    {
        $query = "select * from mismatch_user where user_id = $mismatch_user_id";
        $data = mysqli_query($dbc,$query);
        $row = mysqli_fetch_array($data);
        ?>
        <h1>Your mismatch:</h1>
        <p><strong>Name: </strong><?php  echo $row['first_name'] . $row['last_name'];?></p>
        <p><strong>Birth date: </strong><?php  echo $row['birthdate'];?></p>
        <p><strong>City: </strong><?php  echo $row['city'];?></p>
        <img src="<?php echo MM_UPLOADPATH . $row['picture']?>" alt="">
        <p><strong>Match topics:</strong></p>
        <?php
        foreach($topics as $topic)
        {
            echo '<span>'.$topic.' </span>';
        }
        ?>
        <br/>
        <img src="<?php echo MM_UPLOADPATH .$_SESSION['user_id'] . '_bargraph.png' ; ?>" alt="Bar graph">
        <?php
        echo '<p><a href = "viewprofile.php?user_id='.$mismatch_user_id.'">View profile</a></p>';
    }
    mysqli_close($dbc);
    require_once('footer.php');
?>