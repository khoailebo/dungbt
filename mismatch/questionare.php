<?php
$title_ms = "Questionare";
require_once('header.php');
require_once('appvars.php');
require_once('connectvars.php');
require_once('setsession.php');
if (!isset($_SESSION['user_id'])) {
    echo '<p>You have to <a href ="login.php">login</a> to enter this page</p>';
    exit();
}
require_once('nav.php');
$dbc = mysqli_connect(DB_HOST, DB_USER, DB_PASSWORD, DB_NAME);
$query = "select * from mismatch_response where user_id = " . $_SESSION['user_id'] . " limit 1";
$data = mysqli_query($dbc, $query);
if (mysqli_num_rows($data) == 0) {
    $query = "select topic_id from mismatch_topic";
    $topic_ids = array();
    $data = mysqli_query($dbc, $query);
    while ($row = mysqli_fetch_array($data)) {
        array_push($topic_ids, $row['topic_id']);
        // $query2 = "insert into mismatch_reponse (user_id,topic_id) value(".$_SESSION['user_id'].",".$row['topic_id'].")";
    }
    foreach ($topic_ids as $topic_id) {
        $query2 = "insert into mismatch_response (user_id,topic_id) value(" . $_SESSION['user_id'] . ",$topic_id)";
        mysqli_query($dbc, $query2);
    }
}
if (isset($_POST['submit'])) {
    foreach ($_POST as $reponse_id => $reponse) {
        if(is_numeric($reponse_id))
        {
            $query = "update mismatch_response set reponse = $reponse where reponse_id = $reponse_id";
            mysqli_query($dbc, $query);
        }
    }
}
$reponses = array();
$query = "select mr.reponse_id, mr.reponse,mt.name as topic_name, mc.name as category_name ".
"from mismatch_response as mr ".
"inner join mismatch_topic as mt using (topic_id) ".
"inner join mismatch_category as mc using (category_id) ".
"where mr.user_id = ".$_SESSION['user_id'];
$data = mysqli_query($dbc,$query);
while($row = mysqli_fetch_array($data))
{
    $reponses[] = $row;
}
mysqli_close($dbc);
//Generate form
?>
<form action="<?php echo $_SERVER['PHP_SELF']; ?>" method="post">
    <fieldset>
        <?php
        $category = $reponses[0]['category_name'];
        ?>
        <legend><?php echo $category; ?></legend>
        <?php
        foreach ($reponses as $reponse) {
            if($reponse['category_name'] != $category)
            {
                $category = $reponse['category_name'];
                ?>
                </fieldset>
                <fieldset>
                <legend><?php echo $category; ?></legend>
                <?php
            }
            ?>
            <label for="<?php echo $reponse['reponse_id'];?>" <?php echo $reponse['reponse'] == null?'class = "error"':'' ?>><?php echo $reponse['topic_name']; ?></label>
            <input type="radio" id="<?php echo $reponse['reponse_id']; ?>" name="<?php echo $reponse['reponse_id']; ?>" value="1" <?php echo $reponse['reponse'] == 1?'checked="checked"':''; ?>>Love
            <input type="radio" id="<?php echo $reponse['reponse_id']; ?>" name="<?php echo $reponse['reponse_id']; ?>" value="2" <?php echo $reponse['reponse'] == 2?'checked="checked"':''; ?>>Hate <br/>
            <?php
        }
        ?>
    </fieldset>
    <input type="submit" name="submit" value="Submit">
</form>
<?php
require_once('footer.php');
?>